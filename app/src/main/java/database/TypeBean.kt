package database

// the type of the income and the expense
class TypeBean {
    var id = 0
    var typeName //typename
            : String? = null
    var imageId // the id of the image
            = 0
    var selectedImageId // the id of the selected image
            = 0
    var kind //income:1 expense:0
            = 0

    constructor() {}
    constructor(id: Int, typename: String?, imageId: Int, simageId: Int, kind: Int) {
        this.id = id
        this.typeName = typename
        this.imageId = imageId
        this.selectedImageId = simageId
        this.kind = kind
    }
}