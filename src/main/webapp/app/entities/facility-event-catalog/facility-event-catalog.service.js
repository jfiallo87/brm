(function() {
    'use strict';
    angular
        .module('brmApp')
        .factory('FacilityEventCatalog', FacilityEventCatalog);

    FacilityEventCatalog.$inject = ['$resource'];

    function FacilityEventCatalog ($resource) {
        var resourceUrl =  'api/facility-event-catalogs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
