services.factory('AppAdminService', function($resource, config) {
	return $resource(config.server + '/ad/app', {}, {
		delete: {
			method: 'DELETE',
			url: config.server + '/ad/app/:guid',
			params: {guid: '@guid'},
			isArray: false
		},
		create: {
			method: 'PUT',
			url: config.server + '/ad/app',
			isArray: false
		},
		save: {
			method: 'POST',
			url: config.server + '/ad/app',
			isArray: false
		},
		get: {
			method: 'GET',
			url: config.server + '/ad/app/:guid',
			params: {guid: '@guid'},
			isArray: false
		},
		all: {
			method: 'GET',
			url: config.server + '/ad/app',
			isArray: true
		},
		sys: {
			method: 'GET',
			url: config.server + '/ad/app/:guid/sys',
			params: {guid: '@guid'},
			isArray: true
		},
		addscm: {
			method: 'PUT',
			url: config.server + '/ad/app/:guid/scm/:id',
			params: {guid: '@guid', id: '@id'},
			isArray: false
		},
		scm: {
			method: 'GET',
			url: config.server + '/ad/app/:guid/scm',
			params: {guid: '@guid'},
			isArray: false
		},
		list: {
			method: 'GET',
			url: config.server + '/ad/app/list',
			isArray: false
		},		
		project: {
			method: 'GET',
			url: config.server + '/ad/app/:guid/project',
			isArray: false
		},	
		addproject: {
			method: 'PUT',
			url: config.server + '/ad/app/:guid/project/:id',
			params: {guid: '@guid', id: '@id'},
			isArray: false
		}
	});
});	