package com.morpheusdata.datasets

import com.morpheusdata.core.MorpheusContext
import com.morpheusdata.core.Plugin
import com.morpheusdata.core.data.DatasetInfo
import com.morpheusdata.core.data.DatasetQuery
import com.morpheusdata.core.providers.AbstractDatasetProvider
import com.morpheusdata.response.ServiceResponse
import io.reactivex.rxjava3.core.Observable
import groovy.util.logging.Slf4j

@Slf4j
class ExternalApiDatasetProvider extends AbstractDatasetProvider<Map, Long> {

	public static final providerName = 'External API Dataset Provider'
	public static final providerNamespace = 'example'
	public static final providerKey = 'externalApiDatasetExample'
	public static final providerDescription = 'A set of data from an external API'

	static apiService

	ExternalApiDatasetProvider(Plugin plugin, MorpheusContext morpheus) {
		this.plugin = plugin
		this.morpheusContext = morpheus
		this.apiService = new ApiService()
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

	Class<Map> getItemType() {
		return Map.class
	}

	Observable<Map> list(DatasetQuery query) {
		Map authConfig = apiService.getAuthConfig(query.parameters)
		ServiceResponse apiResults = apiService.listShips(authConfig)
		if(apiResults.success) {
			return Observable.fromIterable((List<Map>)apiResults.data)
		}

		return Observable.empty()
	}

	Observable<Map> listOptions(DatasetQuery query) {
		return list(query).map { [name: it.name, value: it.id] }
	}

	Map fetchItem(Object value) {
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

	Map item(Long value) {
		def rtn = list(new DatasetQuery()).find{ it.id == value }
		return rtn
	}

	String itemName(Map item) {
		return item.name
	}

	Long itemValue(Map item) {
		return (Long)item.id
	}

	@Override
	boolean isPlugin() {
		return true
	}
}
