package com.morpheusdata.datasets

import com.morpheusdata.response.ServiceResponse

class ApiService {

	Map getAuthConfig() {
		return [
			"url": "https://api.example.com",
			"username": "example",
			"password": "-U8e6f4!q.H"
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
