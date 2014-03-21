services.factory('AgentAdminService', function($resource, config) {
	return $resource(config.server + '/agent', {}, {
		delete: {
			method: 'DELETE',
			url: config.server + '/ad/agent/:guid',
			params: {guid: '@guid'},
			isArray: false
		},
		create: {
			method: 'PUT',
			url: config.server + '/ad/agent',
			isArray: false
		},
		save: {
			method: 'POST',
			url: config.server + '/ad/agent',
			isArray: false
		},
		get: {
			method: 'GET',
			url: config.server + '/ad/agent/:guid',
			params: {guid: '@guid'},
			isArray: false
		},
		all: {
			method: 'GET',
			url: config.server + '/ad/agent',
			isArray: true
		},
		pswd: {
			method: 'POST',
			url: config.server + '/ad/agent/:guid/password',
			params: {guid: '@guid'},
			isArray: false
		},
		sys: {
			method: 'GET',
			url: config.server + '/ad/agent/:guid/sys',
			params: {guid: '@guid'},
			isArray: true
		},		
		types: {
			method: 'GET',
			url: config.server + '/ad/agent/types',
			isArray: false
		},
		list: {
			method: 'GET',
			url: config.server + '/ad/agent/list',
			isArray: false
		}		
	});
});