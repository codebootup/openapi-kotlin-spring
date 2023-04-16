package com.codebootup.codegenerator.builder

import com.codebootup.codegenerator.model.DataClass
import com.codebootup.codegenerator.model.DataInterface
import com.codebootup.codegenerator.model.DataSubClass
import com.codebootup.codegenerator.model.GetOperation
import com.codebootup.codegenerator.model.KotlinSpringModel
import com.codebootup.codegenerator.model.PostOperation
import org.openapi4j.parser.model.v3.OpenApi3

class KotlinSpringModelBuilder(
    private val apiPackage: String,
) {
    private val modelPackage = "$apiPackage.model"
    private val springKotlinSchemaObjectBuilder = KotlinSpringSchemaObjectBuilder(modelPackage)
    private val kotlinSpringOperationsBuilder = KotlinSpringOperationsBuilder(modelPackage)

    fun build(inputModel: OpenApi3): KotlinSpringModel {
        val schemaObjects = springKotlinSchemaObjectBuilder.build(inputModel)
        val dataClasses = schemaObjects.filterIsInstance<DataClass>()
        val dataInterfaces = schemaObjects.filterIsInstance<DataInterface>()
        val dataSubClasses = schemaObjects.filterIsInstance<DataSubClass>()

        val operations = kotlinSpringOperationsBuilder.build(inputModel)
        val getOperations = operations.filterIsInstance<GetOperation>()
        val postOperations = operations.filterIsInstance<PostOperation>()

        return KotlinSpringModel(
            apiClassname = inputModel.info.title.replace("\\s".toRegex(), ""),
            apiPackage = apiPackage,
            apiBasePath = inputModel.servers[0].url,
            dataClasses = dataClasses,
            dataInterfaces = dataInterfaces,
            dataSubClasses = dataSubClasses,
            getOperations = getOperations,
            postOperations = postOperations,
        )
    }
}