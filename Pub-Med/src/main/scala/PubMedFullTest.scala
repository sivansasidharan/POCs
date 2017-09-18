
import javax.xml.parsers.{SAXParser, SAXParserFactory}

import org.apache.spark._
import org.apache.spark.graphx.{Graph, Edge, VertexId}
import org.apache.spark.rdd.{PairRDDFunctions, RDD}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.graphx.{GraphXUtils, PartitionStrategy}
import org.apache.spark.graphx.util.GraphGenerators
import scala.util.Try
import org.apache.log4j.{Level, Logger}


object PubMedFull extends Logging {

  def main(args: Array[String]) {

    val sparkConf = new SparkConf().setAppName("PubMed")

    val sc = new SparkContext(sparkConf)
    GraphXUtils.registerKryoClasses(sparkConf)
    val rddArticles = sc.textFile("articles-lines.xml")

     val loadnode = rddArticles.map{ v =>
      val saxfac = SAXParserFactory.newInstance()
      saxfac.setValidating(false)
      saxfac.setFeature("http://xml.org/sax/features/validation", false)
      saxfac.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false)
      saxfac.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
      saxfac.setFeature("http://xml.org/sax/features/external-general-entities", false)
      saxfac.setFeature("http://xml.org/sax/features/external-parameter-entities", false)
      Try(xml.XML.withSAXParser(saxfac.newSAXParser()).loadString(v)\\"article")
    }.filter( _.isSuccess).map(_.get)


       case class Contrib ( contribType: Option[String], surname: Option[String], givenNames: Option[String], phone: Option[String], email: Option[String], fax: Option[String] )

    // Class to hold references
    case class Reference( idRef:Option[String], articleNameRef:Option[String], pmIDFrom: Option[Long], pmIDRef:Option[Long])

    // Class to hold articles
    case class Article(articleName:String, articleAbstract: Option[String],
                       pmID:Option[Long], doi:Option[String],
                       references: Iterator[Reference],
                       contribs: Iterator[Contrib],
                       keywords: List[String])

    // This is the main extraction. All information we need is stored in the article class
    val articles = loadnode.map {
      article =>

        // Helper function to convert String to Options of Long
        def tryToLong( s: String ) = Try(s.toLong).toOption
        def tryToString( s: String) = if (s==null || s.trim.isEmpty) None else Some(s.trim)

        // basic accessible information
        val articleName = (article \\"front"\\ "article-meta" \\ "title-group" \\ "article-title").text
        val articleAbstract = tryToString((article \\ "front" \\ "article-meta" \\ "abstract").text)
        val pmIDFrom = tryToLong((article \\"front" \\ "article-meta" \\"article-id" filter (h => (h \ "@pub-id-type").toString == "pmid")).text)
        val doi = tryToString((article \\"front" \\ "article-meta" \\ "article-id" filter (h => (h \ "@pub-id-type").toString == "doi")).text)

        // create list of references
        val references = (article \\"back" \\"ref").map {
          reference =>
            val idRef = tryToString((reference \ "@id").text)
            val articleNameRef = tryToString((reference \\ "article-title").text)
            val pmIdRef = tryToLong((reference \\ "pub-id" filter (h => (h \ "@pub-id-type").toString == "pmid")).text)
            //            val doiRef = (reference \\ "pub-id" filter (h => (h \ "@pub-id-type").toString == "doi")).text
            //            val namesRef: Iterator[Name] = (reference \\ "person-group"\"name").map {
            //              name =>
            //                val first = (name \\"name"\\ "given-names").text
            //                val surname = (name \\"name"\\ "surname").text
            //                Name(first, surname)
            //            }.toIterator
            Reference(idRef, articleNameRef, pmIDFrom, pmIdRef)
        }.toIterator

        // create list of keywords
        val keywords = (article \\ "front" \\ "article-meta" \\ "kwd-group" \\ "kwd").map{
          keyword => keyword.text.toLowerCase
        }.toList

        // create list of contributors
        val contribs = (article \\ "front" \\ "article-meta" \\ "contrib-group" \\ "contrib").map{
          contrib =>
            val contribType = tryToString((contrib \ "@contrib-type").text)
            val surname = tryToString((contrib \\ "surname").text)
            val givenNames = tryToString((contrib \\ "given-names").text)
            val phone = tryToString((contrib \\ "phone").text)
            val email = tryToString((contrib \\ "email").text)
            val fax = tryToString((contrib \\ "fax").text)
            Contrib(contribType,surname,givenNames,phone,email,fax)
        }.toIterator
        Article(articleName, articleAbstract, pmIDFrom, doi, references, contribs, keywords)
    }
    //.cache()



        println(articles.count)
            articles.foreach {
              article =>
             {
                println(article.articleName + ", " + article.pmID + ", " + article.doi)
              }
          }

    // filter the articles that don't have a defined pmID as they won't be part of the graph
    val filteredArticles = articles.filter{article => article.pmID.isDefined}.cache()

    // create an RDD of vertices - VertexId is a Long, needs to be unique, so pm  ID suits well
    val vertices = filteredArticles.map(article => (article.pmID.get , (article.articleName, article.articleAbstract, article.keywords, article.doi)))

    // create an RDD of edges - need a from and a to ID
    val edges: RDD[Edge[Long]] =
      filteredArticles.flatMap{
        article =>
          article.references.filter{reference => reference.pmIDRef.isDefined}.map{reference =>
            Edge(reference.pmIDFrom.get, reference.pmIDRef.get,1)
          }
      }

    edges.cache()
    vertices.cache()



    //    // NB: printing an RDD, if not cached, will make you lose it and recompute it. Make sure to cache your key RDDs to optimize your flow
        println(s"Vertices count ${vertices.count}")
        println(s"Edges count ${edges.count}")

    //    filteredArticles.unpersist()

    // Define a default article in case there are relationship with missing article
    val defaultArticle = ("Missing", None, List.empty, None)

    // create the graph, making sure we default to a defaultArticle when we have a missing relation (prevents nulls)
    val graph = Graph(vertices, edges, defaultArticle).cache

    // the graph is cached, is computed on the vertice count, which takes some time, and then edge count is blazing fast
    println(s"After graph: Vertices count ${graph.vertices.count}")
    println(s"After graph: Edges count ${graph.edges.count}")

    val stringToSearchFor = "melanoma"
    println(s"Subgraph creation with search string melanoma !!")

try { 
    
    val subgraph = graph.subgraph (
      vpred = (id,article) => article._1.toLowerCase.contains(stringToSearchFor)
    ).cache()

    println( s"Subgraph contains ${subgraph.vertices.count} nodes and ${subgraph.edges.count} edges")

    val prGraph = subgraph.staticPageRank(5).cache

    val titleAndPrGraph = subgraph.outerJoinVertices(prGraph.vertices) {
      (v, title, rank) => (rank.getOrElse(0.0), title)
    }

    titleAndPrGraph.vertices.top(13) {
      Ordering.by((entry: (VertexId, (Double, _))) => entry._2._1)
    }.foreach(t => println(t._2._2._1 + ": " + t._2._1 + ", id:" + t._1))

    val subgraphPR = titleAndPrGraph.subgraph (
      vpred = (id,article) => article._2._1.toLowerCase.contains(stringToSearchFor)
        || article._2._3.exists(keyword => keyword.contains(stringToSearchFor))
        || (article._2._2 match {
        case None => false
        case Some(articleAbstract) => articleAbstract.toLowerCase.contains(stringToSearchFor)
      })
    ).cache()

    subgraphPR.vertices.top(13) {
      Ordering.by((entry: (VertexId, (Double, _))) => entry._2._1)
    }.foreach(t => println(t._2._2._1 + ": " + t._2._1 + ", id:" + t._1))

    //    sc.stop()

} catch {

  case e: Exception => println(s"Subgraph Creation Failed ---> exception caught: " + e)
        }
}

}
