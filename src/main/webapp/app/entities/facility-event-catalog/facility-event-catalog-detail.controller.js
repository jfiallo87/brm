(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('FacilityEventCatalogDetailController', FacilityEventCatalogDetailController);

    FacilityEventCatalogDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'FacilityEventCatalog', 'FacilityCatalog', 'EventCatalog', 'FacilityEventSeatingCatalog', 'FacilityEventServiceCatalog', 'FacilityEventSchedule'];

    function FacilityEventCatalogDetailController($scope, $rootScope, $stateParams, entity, FacilityEventCatalog, FacilityCatalog, EventCatalog, FacilityEventSeatingCatalog, FacilityEventServiceCatalog, FacilityEventSchedule) {
        var vm = this;

        vm.facilityEventCatalog = entity;

        var unsubscribe = $rootScope.$on('brmApp:facilityEventCatalogUpdate', function(event, result) {
            vm.facilityEventCatalog = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
