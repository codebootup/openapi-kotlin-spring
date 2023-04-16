package com.codebootup.codegenerator.builder

import com.codebootup.codegenerator.ModelBuilder
import com.codebootup.codegenerator.model.KotlinSpringModel

class ModelInputOutputBuilder(
    private val inputBuilder: OpenApiInputModelBuilder,
    private val outputBuilder: KotlinSpringModelBuilder,
) : ModelBuilder<KotlinSpringModel> {

    override fun build(): KotlinSpringModel {
        return outputBuilder.build(inputBuilder.build())
    }
}
