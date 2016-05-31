(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('FacilityCatalogDetailController', FacilityCatalogDetailController);

    FacilityCatalogDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'FacilityCatalog', 'FacilityEventCatalog', 'FacilitySchedule'];

    function FacilityCatalogDetailController($scope, $rootScope, $stateParams, entity, FacilityCatalog, FacilityEventCatalog, FacilitySchedule) {
        var vm = this;

        vm.facilityCatalog = entity;

        var unsubscribe = $rootScope.$on('brmApp:facilityCatalogUpdate', function(event, result) {
            vm.facilityCatalog = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
