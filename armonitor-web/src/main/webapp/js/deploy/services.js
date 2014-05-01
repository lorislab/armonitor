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
			url: config.server + '/deploy',			
			isArray: true
		}		
	});
}]);	