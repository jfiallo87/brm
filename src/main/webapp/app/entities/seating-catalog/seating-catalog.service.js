(function() {
    'use strict';
    angular
        .module('brmApp')
        .factory('SeatingCatalog', SeatingCatalog);

    SeatingCatalog.$inject = ['$resource'];

    function SeatingCatalog ($resource) {
        var resourceUrl =  'api/seating-catalogs/:id';

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
