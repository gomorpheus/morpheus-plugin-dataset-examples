package com.morpheusdata.datasets

import com.morpheusdata.response.ServiceResponse

class ApiService {

	Map getAuthConfig(Map opts) {
		return [
			"url": opts.apiUrl,
			"username": opts.username,
			"password": opts.password
		]
	}

	ServiceResponse listShips(Map authConfig) {
		// fetch items from external api
		ServiceResponse results =  ServiceResponse.success(
			[
				[
					"id": 1,
					"name": "Nebuchadnezzar",
					"crew": 9
				],
				[
					"id": 2,
					"name": "Logos",
					"crew": 4
				]
			]
		)

		return results
	}
}
