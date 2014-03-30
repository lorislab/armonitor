services.factory('BTSAdminService', ['$resource','config',
	function($resource, config) {
	return $resource(config.server + '/ad/bts', {}, {
		remove: {
			method: 'DELETE',
			url: config.server + '/ad/bts/:guid',
			params: {guid: '@guid'},
			isArray: false
		},
		create: {
			method: 'PUT',
			url: config.server + '/ad/bts',
			isArray: false
		},
		save: {
			method: 'POST',
			url: config.server + '/ad/bts',
			isArray: false
		},
		get: {
			method: 'GET',
			url: config.server + '/ad/bts/:guid',
			params: {guid: '@guid'},
			isArray: false
		},
		test: {
			method: 'GET',
			url: config.server + '/ad/bts/:guid/test',
			params: {guid: '@guid'},
			isArray: false
		},
		access: {
			method: 'GET',
			url: config.server + '/ad/bts/:guid/test/:project',
			params: {guid: '@guid', project: '@project'},
			isArray: false
		},		
		pswd: {
			method: 'POST',
			url: config.server + '/ad/bts/:guid/password',
			params: {guid: '@guid'},
			isArray: false
		},
		types: {
			method: 'GET',
			url: config.server + '/ad/bts/types',
			isArray: false
		},
		all: {
			method: 'GET',
			url: config.server + '/ad/bts',
			isArray: true
		},
		list: {
			method: 'GET',
			url: config.server + '/ad/bts/list',
			isArray: false
		}		
	});
}]);