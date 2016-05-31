(function() {
    'use strict';
    angular
        .module('brmApp')
        .factory('FacilitySchedule', FacilitySchedule);

    FacilitySchedule.$inject = ['$resource', 'DateUtils'];

    function FacilitySchedule ($resource, DateUtils) {
        var resourceUrl =  'api/facility-schedules/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.start = DateUtils.convertDateTimeFromServer(data.start);
                        data.end = DateUtils.convertDateTimeFromServer(data.end);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
