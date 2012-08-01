package org.grails.plugin.lesscss

class NodeJSLessException extends RuntimeException {
	NodeJSLessException() {
	}

	NodeJSLessException(String s) {
		super(s)
	}

	NodeJSLessException(String s, Throwable throwable) {
		super(s, throwable)
	}

	NodeJSLessException(Throwable throwable) {
		super(throwable)
	}
}
