(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('FacilityEventScheduleDetailController', FacilityEventScheduleDetailController);

    FacilityEventScheduleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'FacilityEventSchedule', 'FacilityEventCatalog'];

    function FacilityEventScheduleDetailController($scope, $rootScope, $stateParams, entity, FacilityEventSchedule, FacilityEventCatalog) {
        var vm = this;

        vm.facilityEventSchedule = entity;

        var unsubscribe = $rootScope.$on('brmApp:facilityEventScheduleUpdate', function(event, result) {
            vm.facilityEventSchedule = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
