services.factory('UserAdminService', function($resource, config) {
	return $resource(config.server + '/user', {}, {
		delete: {
			method: 'DELETE',
			url: config.server + '/ad/user/:guid',
			params: {guid: '@guid'},
			isArray: false
		},
		create: {
			method: 'PUT',
			url: config.server + '/ad/user',
			isArray: false
		},
		save: {
			method: 'POST',
			url: config.server + '/ad/user',
			isArray: false
		},
		get: {
			method: 'GET',
			url: config.server + '/ad/user/:guid',
			params: {guid: '@guid'},
			isArray: false
		},
		all: {
			method: 'GET',
			url: config.server + '/ad/user',
			isArray: true
		},
		roles: {
			method: 'GET',
			url: config.server + '/ad/user/:guid/role',
			params: {guid: '@guid'},
			isArray: false
		},
		addrole: {
			method: 'PUT',
			url: config.server + '/ad/user/:guid/role/:role',
			params: {guid: '@guid', role: '@role'},
			isArray: false
		},
		removerole: {
			method: 'DELETE',
			url: config.server + '/ad/user/:guid/role/:role',
			params: {guid: '@guid', role: '@role'},
			isArray: false
		},		
		pswd: {
			method: 'POST',
			url: config.server + '/ad/user/:guid/password',
			params: {guid: '@guid'},
			isArray: false
		},
		pswdreset: {
			method: 'PUT',
			url: config.server + '/ad/user/:guid/password',
			params: {guid: '@guid'},
			isArray: false
		}		
	});
});

