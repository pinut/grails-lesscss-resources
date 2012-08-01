package org.grails.plugin.lesscss

import org.lesscss.LessException

class NodeJSLessCompiler {

	boolean compress = false

	static isAvailable() {
		isOperatingSystemSupported() && getNodePath() && getLessPath()
	}

	static isOperatingSystemSupported() {
		switch (System.getProperty("os.name").toLowerCase()) {
			case "linux": true; break
			default: false
		}
	}

	static getLessPath() {
		def p = "/usr/bin/which lessc".execute()
		p.waitFor()
		p.text
	}

	static getNodePath() {
		def p = "/usr/bin/which node".execute()
		p.waitFor()
		p.text
	}

	def compile(input, target) {
		println "compiling less file '$input'..."
		def args = createArgs(input, target)
		def output = execute(args)
		if (output.size()) {
			throw new NodeJSLessException(output.toString())
		}
		println "...done"
	}

	private createArgs(input, target) {
		def node = getNodePath()
		def lessc = getLessPath()
		def args = [node, lessc, "-x"]
		if (compress) args << "--yui-compress"
		args + [input.absolutePath, target.absolutePath]
	}

	private execute(args) {
		def p = args.join(' ').execute()
		def sb = new StringBuilder()
		p.consumeProcessOutput(sb, sb)
		p.waitFor()
		sb
	}
}