services.factory('DeployService', ['$resource','config',
	function($resource, config) {
	return $resource(config.server + '/deploy', {}, {
		deploy: {
			method: 'POST',
			url: config.server + '/deploy',
			isArray: false
		},
		systems: {
			method: 'GET',
			url: config.server + '/deploy/',			
			isArray: true
		},
		builds: {
			method: 'GET',
			url: config.server + '/deploy/:guid',			
			params: {guid: '@guid'},
			isArray: false
		},
		build: {
			method: 'GET',
			url: config.server + '/deploy/:guid/:build',			
			params: {guid: '@guid', build: '@build'},
			isArray: false
		}		
	});
}]);	