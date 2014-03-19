services.factory('SystemAdminService', function($resource, config) {
	return $resource(config.server + '/ad/sys', {}, {
		delete: {
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
		}
	});
});	