package io.github.dotocan.raketa.domain.model.rest


data class OAuthServices(
		val services: List<Service>,
		val success: Boolean
)

data class Service(
		val id: String,
		val name: String,
		val appId: String,
		val buttonLabelText: String,
		val buttonColor: String,
		val buttonLabelColor: String
)