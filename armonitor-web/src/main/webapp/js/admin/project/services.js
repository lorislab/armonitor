services.factory('ProjectAdminService', function($resource, config) {
	return $resource(config.server + '/ad/pr', {}, {
		delete: {
			method: 'DELETE',
			url: config.server + '/ad/pr/:guid',
			params: {guid: '@guid'},
			isArray: false
		},
		create: {
			method: 'PUT',
			url: config.server + '/ad/pr',
			isArray: false
		},
		save: {
			method: 'POST',
			url: config.server + '/ad/pr',
			isArray: false
		},
		get: {
			method: 'GET',
			url: config.server + '/ad/pr/:guid',
			params: {guid: '@guid'},
			isArray: false
		},
		all: {
			method: 'GET',
			url: config.server + '/ad/pr',
			isArray: true
		},
		bts: {
			method: 'GET',
			url: config.server + '/ad/pr/:guid/bts',
			params: {guid: '@guid'},
			isArray: false
		},
		app: {
			method: 'GET',
			url: config.server + '/ad/pr/:guid/app',
			params: {guid: '@guid'},
			isArray: true
		},
		list: {
			method: 'GET',
			url: config.server + '/ad/pr/list',
			isArray: false
		},		
		addbts: {
			method: 'PUT',
			url: config.server + '/ad/pr/:guid/bts/:id',
			params: {guid: '@guid', id: '@id'},
			isArray: false
		}		
	});
});	