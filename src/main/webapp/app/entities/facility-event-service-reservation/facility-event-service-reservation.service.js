(function() {
    'use strict';
    angular
        .module('brmApp')
        .factory('FacilityEventServiceReservation', FacilityEventServiceReservation);

    FacilityEventServiceReservation.$inject = ['$resource'];

    function FacilityEventServiceReservation ($resource) {
        var resourceUrl =  'api/facility-event-service-reservations/:id';

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
