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
		getPathTo "lessc"
	}

	static getNodePath() {
		getPathTo "node"
	}

	static getPathTo(program) {
		def p = "/usr/bin/which $program".execute()
		p.waitFor()
		def path = p.text.trim()
		new File(path).exists() ? path : null
	}

	def compile(input, target) {
		print "compiling less file '$input'... "
		def args = createArgs(input, target)
		def (process, output) = execute(args)
		if (process.exitValue()) {
			throw new NodeJSLessException(output.toString())
		}
		println "done"
	}

	private createArgs(input, target) {
		def node = getNodePath()
		def lessc = getLessPath()
		def args = [node, lessc, "-x"]
		if (compress) args << "--yui-compress"
		args + [input.absolutePath, target.absolutePath]
	}

	private execute(args) {
		def p = args.execute()
		def sb = new StringBuilder()
		p.consumeProcessOutput(sb, sb)
		p.waitFor()
		[p, sb]
	}
}