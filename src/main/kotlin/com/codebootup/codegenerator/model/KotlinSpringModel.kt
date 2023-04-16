package com.codebootup.codegenerator.model

data class KotlinSpringModel(
    val apiClassname: String,
    val apiPackage: String,
    val apiBasePath: String,
    val dataClasses: List<DataClass>,
    val dataInterfaces: List<DataInterface>,
    val dataSubClasses: List<DataSubClass>,
    val getOperations: List<GetOperation>,
    val postOperations: List<PostOperation>,
) {
    @Suppress("unused")
    val apiImports: List<String> = (
        (getOperations + postOperations)
            .map { it.responseType.packageImport }
            .toSet() + (
            if ((getOperations + postOperations)
                    .flatMap { it.pathVariables }
                    .isNotEmpty()
            ) {
                setOf("org.springframework.web.bind.annotation.PathVariable")
            } else {
                setOf()
            }
            ) + (
            if (postOperations.isNotEmpty()) {
                postOperations
                    .map { it.requestBody.type.packageImport }
                    .toSet() + "org.springframework.web.bind.annotation.RequestBody"
            } else {
                setOf()
            }
            )
        ).toList().sorted()
}
