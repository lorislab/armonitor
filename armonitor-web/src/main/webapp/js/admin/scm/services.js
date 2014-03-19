services.factory('SCMAdminService', function($resource, config) {
	return $resource(config.server + '/ad/scm', {}, {
		delete: {
			method: 'DELETE',
			url: config.server + '/ad/scm/:guid',
			params: {guid: '@guid'},
			isArray: false
		},
		create: {
			method: 'PUT',
			url: config.server + '/ad/scm',
			isArray: false
		},
		save: {
			method: 'POST',
			url: config.server + '/ad/scm',
			isArray: false
		},
		get: {
			method: 'GET',
			url: config.server + '/ad/scm/:guid',
			params: {guid: '@guid'},
			isArray: false
		},
		pswd: {
			method: 'POST',
			url: config.server + '/ad/scm/:guid/password',
			params: {guid: '@guid'},
			isArray: false
		},
		types: {
			method: 'GET',
			url: config.server + '/ad/scm/types',
			isArray: false
		},
		apps: {
			method: 'GET',
			url: config.server + '/ad/scm/:guid/app',
			params: {guid: '@guid'},
			isArray: true
		},
		app: {
			method: 'GET',
			url: config.server + '/ad/scm/:guid/app/:app',
			params: {guid: '@guid', app: '@app'},
			isArray: false
		},
		add: {
			method: 'PUT',
			url: config.server + '/ad/scm/:guid/app/:app',
			params: {guid: '@guid', app: '@app'},
			isArray: false
		},
		all: {
			method: 'GET',
			url: config.server + '/ad/scm',
			isArray: true
		}
	});
});