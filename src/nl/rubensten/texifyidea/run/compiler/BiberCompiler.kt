package nl.rubensten.texifyidea.run.compiler

import com.intellij.openapi.project.Project
import nl.rubensten.texifyidea.run.BibtexRunConfiguration

/**
 * @author Thomas Schouten
 */
internal object BiberCompiler : Compiler<BibtexRunConfiguration> {

    override val displayName = "Biber"
    override val executableName = "biber"

    override fun getCommand(runConfig: BibtexRunConfiguration, project: Project): List<String>? {
        val command = mutableListOf<String>()

        command.apply {
            if (runConfig.compilerPath != null) {
                add(runConfig.compilerPath!!)
            }
            else add(executableName)

            // Biber can find auxiliary files, but the flag is different from bibtex.
            // The following flag assumes the command is executed in the directory where the .bcf control file is.
            // The extra directory added is the directory from which the path to the .bib resource file is searched as specified in the .bcf file.
            add("--input-directory=${runConfig.mainFile?.parent?.path ?: ""}")

            runConfig.compilerArguments?.let { addAll(it.split("""\s+""".toRegex())) }

            add(runConfig.mainFile?.nameWithoutExtension ?: return null)
        }

        return command.toList()
    }
}