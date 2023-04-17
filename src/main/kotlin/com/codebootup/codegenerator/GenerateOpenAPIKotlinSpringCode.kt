package com.codebootup.codegenerator

import com.codebootup.codegenerator.builder.KotlinSpringModelBuilder
import com.codebootup.codegenerator.builder.ModelInputOutputBuilder
import com.codebootup.codegenerator.builder.OpenApiInputModelBuilder
import com.codebootup.codegenerator.model.DataClass
import com.codebootup.codegenerator.model.DataInterface
import com.codebootup.codegenerator.model.DataPrimitiveClass
import com.codebootup.codegenerator.model.DataSubClass
import com.codebootup.codegenerator.model.EnumClass
import com.codebootup.codegenerator.model.KotlinSpringModel
import org.thymeleaf.TemplateEngine
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import org.thymeleaf.templateresolver.ITemplateResolver
import java.io.File

class GenerateOpenAPIKotlinSpringCode(
    private val config: Map<String, String>,
) : ModelBuilder<CodeRenderer> {

    companion object {
        const val KOTLIN_SUFFIX = "kt"
    }

    override fun build(): CodeRenderer {
        val specification = config["openApiSpecification"]
            ?: throw RuntimeException("Config property [openApiSpecification] must be specified.")
        val apiPackage = config["apiPackage"]
            ?: throw RuntimeException("Config property [apiPackage] must be specified.")
        val sourceDirectory = config["sourceDirectory"] ?: "src${File.separator}main${File.separator}kotlin"

        val apiPackageDirectory = apiPackage.replace(".", File.separator)
        val modelPackageDirectory = "$apiPackageDirectory${File.separator}model"

        val modelBuilder = ModelInputOutputBuilder(
            inputBuilder = OpenApiInputModelBuilder(File(specification)),
            outputBuilder = KotlinSpringModelBuilder(apiPackage),
        )

        val templateEngine = TemplateEngine()
        templateEngine.addTemplateResolver(templateResolver())

        return CodeRenderer(
            modelBuilder = modelBuilder,
            templateEngine = ThymeleafTemplateEngine(templateEngine),
        )
            .addTemplate(
                TemplateRenderContext(
                    template = "api",
                    fileNamingStrategy = ItemInFocusFileNamingStrategy(
                        pathExpression = KotlinSpringModel::apiClassname.name,
                        suffix = KOTLIN_SUFFIX,
                    ),
                    location = TemplateLocation(baseDirectory = sourceDirectory, fileDirectory = apiPackageDirectory),
                ),
            )
            .addTemplate(
                TemplateRenderContext(
                    template = "dataClass",
                    fileNamingStrategy = ItemInFocusFileNamingStrategy(
                        pathExpression = DataClass::classname.name,
                        suffix = KOTLIN_SUFFIX,
                    ),
                    modelPathInFocus = KotlinSpringModel::dataClasses.name,
                    location = TemplateLocation(baseDirectory = sourceDirectory, fileDirectory = modelPackageDirectory),
                ),
            )
            .addTemplate(
                TemplateRenderContext(
                    template = "dataInterface",
                    fileNamingStrategy = ItemInFocusFileNamingStrategy(
                        pathExpression = DataInterface::classname.name,
                        suffix = KOTLIN_SUFFIX,
                    ),
                    modelPathInFocus = KotlinSpringModel::dataInterfaces.name,
                    location = TemplateLocation(baseDirectory = sourceDirectory, fileDirectory = modelPackageDirectory),
                ),
            )
            .addTemplate(
                TemplateRenderContext(
                    template = "dataSubClass",
                    fileNamingStrategy = ItemInFocusFileNamingStrategy(
                        pathExpression = DataSubClass::classname.name,
                        suffix = KOTLIN_SUFFIX,
                    ),
                    modelPathInFocus = KotlinSpringModel::dataSubClasses.name,
                    location = TemplateLocation(baseDirectory = sourceDirectory, fileDirectory = modelPackageDirectory),
                ),
            )
            .addTemplate(
                TemplateRenderContext(
                    template = "enumClass",
                    fileNamingStrategy = ItemInFocusFileNamingStrategy(
                        pathExpression = EnumClass::classname.name,
                        suffix = KOTLIN_SUFFIX,
                    ),
                    modelPathInFocus = KotlinSpringModel::enumClasses.name,
                    location = TemplateLocation(baseDirectory = sourceDirectory, fileDirectory = modelPackageDirectory),
                ),
            )
            .addTemplate(
                TemplateRenderContext(
                    template = "dataPrimitiveClass",
                    fileNamingStrategy = ItemInFocusFileNamingStrategy(
                        pathExpression = DataPrimitiveClass::classname.name,
                        suffix = KOTLIN_SUFFIX,
                    ),
                    modelPathInFocus = KotlinSpringModel::dataPrimitiveClasses.name,
                    location = TemplateLocation(baseDirectory = sourceDirectory, fileDirectory = modelPackageDirectory),
                ),
            )
    }

    private fun templateResolver(): ITemplateResolver {
        val resolver = ClassLoaderTemplateResolver()
        resolver.prefix = "templates/"
        resolver.templateMode = TemplateMode.TEXT
        resolver.suffix = ".template"
        return resolver
    }
}
