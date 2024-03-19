package com.morpheusdata.datasets

import com.morpheusdata.core.MorpheusContext
import com.morpheusdata.core.Plugin
import com.morpheusdata.core.data.DataQuery
import com.morpheusdata.core.data.DatasetInfo
import com.morpheusdata.core.data.DatasetQuery
import com.morpheusdata.core.providers.AbstractDatasetProvider
import com.morpheusdata.model.Network
import io.reactivex.Observable
import groovy.util.logging.Slf4j

@Slf4j
class PluginApiDatasetProvider extends AbstractDatasetProvider<Network, Long> {

	public static final providerName = 'Plugin API Dataset Provider'
	public static final providerNamespace = 'example'
	public static final providerKey = 'pluginApiDatasetExample'
	public static final providerDescription = 'A list of networks retrieved from the Morpheus plugin API'

	PluginApiDatasetProvider(Plugin plugin, MorpheusContext morpheus) {
		this.plugin = plugin
		this.morpheusContext = morpheus
	}

	@Override
	DatasetInfo getInfo() {
		new DatasetInfo(
			name: providerName,
			namespace: providerNamespace,
			key: providerKey,
			description: providerDescription
		)
	}

	@Override
	Class<Network> getItemType() {
		return Network.class
	}

	/**
	 * List the available datacenters for a given cloud stored in the local cache.
	 * @param datasetQuery
	 * @return a list of datacenters represented as ReferenceData
	 */
	@Override
	Observable<Network> list(DatasetQuery datasetQuery) {
		datasetQuery.max = 10
		return morpheus.async.network.list(datasetQuery)
	}

	/**
	 * List the available datacenters for a given cloud stored in the local cache or fetched from the API of no cached data is available.
	 * @param datasetQuery
	 * @return a list of datacenters represented as a collection of key/value pairs.
	 */
	@Override
	Observable<Map> listOptions(DatasetQuery datasetQuery) {
		return list(datasetQuery).map { [name: it.name, value: it.id] }
	}

	@Override
	Network fetchItem(Object value) {
		def rtn = null
		if(value instanceof Long) {
			rtn = item((Long)value)
		} else if(value instanceof CharSequence) {
			def longValue = value.isNumber() ? value.toLong() : null
			if(longValue) {
				rtn = item(longValue)
			}
		}
		return rtn
	}

	Network item(Long value) {
		return list(new DataQuery().withFilter("id", value).max(1)).toList().blockingGet().first()
	}

	@Override
	String itemName(Network item) {
		return item.name
	}

	@Override
	Long itemValue(Network item) {
		return item.id
	}

	@Override
	boolean isPlugin() {
		return true
	}
}