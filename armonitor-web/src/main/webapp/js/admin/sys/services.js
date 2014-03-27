services.factory('SystemAdminService', ['$resource','config',
	function($resource, config) {
	return $resource(config.server + '/ad/sys', {}, {
		remove: {
			method: 'DELETE',
			url: config.server + '/ad/sys/:guid',
			params: {guid: '@guid'},
			isArray: false
		},
		create: {
			method: 'PUT',
			url: config.server + '/ad/sys',
			isArray: false
		},
		save: {
			method: 'POST',
			url: config.server + '/ad/sys',
			isArray: false
		},
		get: {
			method: 'GET',
			url: config.server + '/ad/sys/:guid',
			params: {guid: '@guid'},
			isArray: false
		},
		all: {
			method: 'GET',
			url: config.server + '/ad/sys',
			isArray: true
		},
		app: {
			method: 'GET',
			url: config.server + '/ad/sys/:guid/app',
			params: {guid: '@guid'},
			isArray: false			
		},
		addapp: {
			method: 'PUT',
			url: config.server + '/ad/sys/:guid/app/:app',
			params: {guid: '@guid', app: '@app'},
			isArray: false				
		},
		agent: {
			method: 'GET',
			url: config.server + '/ad/sys/:guid/agent',
			params: {guid: '@guid'},
			isArray: false			
		},
		addagent: {
			method: 'PUT',
			url: config.server + '/ad/sys/:guid/agent/:agent',
			params: {guid: '@guid', agent: '@agent'},
			isArray: false				
		},
		roles: {
			method: 'GET',
			url: config.server + '/ad/sys/:guid/role',
			params: {guid: '@guid'},
			isArray: false
		},
		addrole: {
			method: 'PUT',
			url: config.server + '/ad/sys/:guid/role/:role',
			params: {guid: '@guid', role: '@role'},
			isArray: false
		},
		removerole: {
			method: 'DELETE',
			url: config.server + '/ad/sys/:guid/role/:role',
			params: {guid: '@guid', role: '@role'},
			isArray: false
		},
		deleteKey: {
			method: 'DELETE',
			url: config.server + '/ad/sys/:guid/key',
			params: {guid: '@guid'},
			isArray: false
		},
		generateKey: {
			method: 'GET',
			url: config.server + '/ad/sys/:guid/key',
			params: {guid: '@guid'},
			isArray: false
		}		
	});
}]);	