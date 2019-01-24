package doit.doittestapplication.data.api.model

data class PictureResponse(val images : List<Image>, val gif : List<Image> ) {

    class Image(val id: Long, val description: String, val hashtag: String, val parameters: Parameters, val smallImagePath: String, val bigImagePath: String, val created: String)

    class Parameters(val coordinates: MapCoordinates, val weather: String)

}

