package com.carvalho.pokedex.model.species

import com.carvalho.pokedex.model.commonModel.*
import com.carvalho.pokedex.model.evolution.chains.EvolutionChain
import com.carvalho.pokedex.model.species.encounterArea.PalParkEncounterArea
import com.carvalho.pokedex.model.species.genus.Genus
import com.carvalho.pokedex.model.species.speciesDex.PokemonSpeciesDexEntry
import com.carvalho.pokedex.model.species.speciesVariety.PokemonSpeciesVariety
import com.google.gson.annotations.SerializedName

data class PokemonSpecies(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("order")
    val order: Int,
    @SerializedName("gender_rate")
    val gender_rate: Int,
    @SerializedName("capture_rate")
    val capture_rate: Int,
    @SerializedName("base_happiness")
    val base_happiness: Int,
    @SerializedName("is_baby")
    val is_baby: Boolean,
    @SerializedName("is_legendary")
    val is_legendary: Boolean,
    @SerializedName("is_mythical")
    val is_mythical: Boolean,
    @SerializedName("hatch_counter")
    val hatch_counter: Int,
    @SerializedName("has_gender_differences")
    val has_gender_differences: Boolean,
    @SerializedName("forms_switchable")
    val forms_switchable: Boolean,
    @SerializedName("growth_rate")
    val growth_rate: NamedAPIResource,
    @SerializedName("pokedex_numbers")
    val pokedex_numbers: List<PokemonSpeciesDexEntry>,
    @SerializedName("egg_groups")
    val egg_groups: List<NamedAPIResource>,
    @SerializedName("color")
    val color: NamedAPIResource,
    @SerializedName("shape")
    val shape: NamedAPIResource,
    @SerializedName("evolves_from_species")
    val evolves_from_species: NamedAPIResource,
    @SerializedName("evolution_chain")
    val evolution_chain: APIResource,
    @SerializedName("habitat")
    val habitat: NamedAPIResource,
    @SerializedName("generation")
    val generation: NamedAPIResource,
    @SerializedName("names")
    val names: List<Name>,
    @SerializedName("pal_park_encounters")
    val pal_park_encounters: List<PalParkEncounterArea>,
    @SerializedName("flavor_text_entries")
    val flavor_text_entries: List<FlavorText>,
    @SerializedName("form_descriptions")
    val form_descriptions: List<Description>,
    @SerializedName("genera")
    val genera: List<Genus>,
    @SerializedName("varieties")
    val varieties: List<PokemonSpeciesVariety>

)
