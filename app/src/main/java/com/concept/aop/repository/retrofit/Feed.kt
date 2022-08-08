package com.concept.aop.repository.retrofit

import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamAsAttribute
import com.thoughtworks.xstream.annotations.XStreamImplicit
import com.thoughtworks.xstream.annotations.XStreamOmitField
import org.simpleframework.xml.*


/*
 * Copyright (c) 2022, CTL and/or its affiliates. All rights reserved.
 * Created by nkjha on 2022-07-29.
 */

@XStreamAlias("feed")
@Root(name = "feed", strict = false)
data class Feed @JvmOverloads constructor (
    @field:Element(name = "title")
    @param:Element(name = "title")
    @field:Path("feed")
    @param:Path("feed")
    var title: String = "",
    @field:Element(name = "id")
    @param:Element(name = "id")
    @field:Path("feed")
    @param:Path("feed")
    var id: String = "",
    @field:Element(name = "updated")
    @param:Element(name = "updated")
    @field:Path("feed")
    @param:Path("feed")
    var updated: String = "",
    @field:Element(name = "icon")
    @param:Element(name = "icon")
    @field:Path("feed")
    @param:Path("feed")
    var icon: String = "",
    @field: Element(name = "author")
    var author: Author? = null,
    @field:Element(name = "rights")
    @param:Element(name = "rights")
    @field:Path("feed")
    @param:Path("feed")
    var rights: String = "",
    @XStreamImplicit(itemFieldName="entry")
    @field:ElementList(name = "entry", inline = true, required = false)
    @param:ElementList(name = "entry", inline = true, required = false)
    @field:Path("feed")
    @param:Path("feed")
    var entryList: List<Entry>? = null,
    @XStreamImplicit(itemFieldName="link")
    @field:ElementList(name = "link", inline = true, required = false)
    @param:ElementList(name = "link", inline = true, required = false)
    @field:Path("feed")
    @param:Path("feed")
    var feedlinkList: List<Link>? = null,
    @XStreamOmitField()
    private val ignoredElement: Any? = null
)
@XStreamAlias("author")
@Root(name = "author", strict = false)
data class Author  @JvmOverloads constructor(
    @field:Element(name = "name")
    @param:Element(name = "name")
    @field:Path("author")
    @param:Path("author")
    var name: String = "",
    @field:Element(name = "uri")
    @param:Element(name = "uri")
    @field:Path("author")
    @param:Path("author")
    var uri: String = ""
)

@XStreamAlias("entry")
@Root(name = "entry", strict = false)
data class Entry  @JvmOverloads constructor(
    /**
     * @return the title
     */
    /**
     * @param title the title to set
     */
    @field:Element(name = "title")
    @param:Element(name = "title")
    var title: String="",
 /**
     * @return the id it having value as link
  */
    /**
     * @param link  the link  to set
     */
    @XStreamAlias("id")
    @field:Element(name = "id")
    @param:Element(name = "id")
    var id_link: String="",

    /**
     * @return the link
     */
    /**
     * @param link the link to set
     */
    @XStreamImplicit(itemFieldName="link")
    @field:ElementList(name = "link", inline = true, required = false)
    @param:ElementList(name = "link", inline = true, required = false)
    @field:Path("entry")
    @param:Path("entry")
    var link: List<Link>? = null,/*
    @field:Element(name = "link")
    @param:Element(name = "link")
    var link: Link? = null,*/

    /**
     * @return the name
     */
    /**
     * @param name the name to set
     */
    @XStreamAlias("im:name")
    @field:Element(name = "im:name")
    @param:Element(name = "im:name")
    var headTitle: String="",
    @XStreamAlias("im:artist")
    @field:Element(name = "im:artist")
    @param:Element(name = "im:artist")
    var artist : String="",
    @XStreamAlias("im:price")
    @field:Element(name = "im:price")
    @param:Element(name = "im:price")
    var price  : String="",
    @XStreamImplicit(itemFieldName="im:image")
    @field:Element(name = "im:image")
    @param:Element(name = "im:image")
    var image  : List<String>? =null,

    @field:Element(name = "rights")
    @param:Element(name = "rights")
    var rights  : String="",
    @XStreamAlias("im:releaseDate")
    @field:Element(name = "im:releaseDate")
    @param:Element(name = "im:releaseDate")
    var releaseDate  : String = "",
    @XStreamOmitField
    private val ignoredElement: Any? = null


)
@XStreamAlias("link")
@Root(name = "link", strict = false)
data class Link  @JvmOverloads constructor(

    @field:Element(name = "im:duration")
    @param:Element(name = "im:duration")
    var duration: Int = 0,
    @XStreamAsAttribute()
    @field:Attribute(name = "href")
    @param:Element(name = "href")
    var href: String = "",

    @XStreamAsAttribute()
    @field:Attribute(name = "type")
    @param:Element(name = "type")
    var type: String = ""
    // type="audio/x-m4a" parse this only if it have audio
    ,
    @XStreamOmitField
    private var ignoredElement: Any? = null
)





