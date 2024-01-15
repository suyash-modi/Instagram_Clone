package com.droid.instagram.Models

class Reel {
    var reelUrl:String=""
    var caption:String=""
    var profileLink:String?=null

    constructor()
    constructor(postUrl: String,caption:String) {
        this.reelUrl = postUrl
        this.caption=caption
    }

    constructor(postUrl: String,caption:String,profileLink:String) {
        this.reelUrl = postUrl
        this.caption = caption
        this.profileLink = profileLink
    }

}