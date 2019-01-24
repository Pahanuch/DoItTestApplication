package doit.doittestapplication.data.api.model

data class LoadPictureResponse(val parameterName: ParameterName) {

    class ParametersData(val addressWeatherData: AddressWeatherData, val smallImage : String, val bigImage : String)

    class AddressWeatherData(val address: String, val weather : String)
}
