package com.ss.nitro.analytics.dyorg.dto;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

public final class EmployeeDTO {
	public final String name;
	public final Children1 children1[];

	@JsonCreator
	public EmployeeDTO(@JsonProperty("name") String name,
			@JsonProperty("children1") Children1[] children1) {
		this.name = name;
		this.children1 = children1;
	}

	public static final class Children1 {
		public final String name;
		public final Children2 children2[];

		@JsonCreator
		public Children1(@JsonProperty("name") String name,
				@JsonProperty("children2") Children2[] children2) {
			this.name = name;
			this.children2 = children2;
		}

		public static final class Children2 {
			public final String name;
			public final Children children[];

			@JsonCreator
			public Children2(@JsonProperty("name") String name,
					@JsonProperty("children") Children[] children) {
				this.name = name;
				this.children = children;
			}

			public static final class Children {
				public final String name;
				public final long size;

				@JsonCreator
				public Children(@JsonProperty("name") String name,
						@JsonProperty("size") long size) {
					this.name = name;
					this.size = size;
				}
			}

			@Override
			public String toString() {
				return "Children2 [name=" + name + ", children=" + children
						+ "]";
			}
		}
	}

	@Override
	public String toString() {
		return "EmployeeDTO [name=" + name + ", children1=" + children1
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

}