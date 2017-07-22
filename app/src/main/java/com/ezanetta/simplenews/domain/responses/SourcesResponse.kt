package com.ezanetta.simplenews.domain.responses

import com.ezanetta.simplenews.domain.model.Source
import com.google.gson.annotations.SerializedName


class SourcesResponse(@SerializedName("sources") val sources: List<Source>)