(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('EventCatalogDetailController', EventCatalogDetailController);

    EventCatalogDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'EventCatalog', 'FacilityEventCatalog'];

    function EventCatalogDetailController($scope, $rootScope, $stateParams, entity, EventCatalog, FacilityEventCatalog) {
        var vm = this;

        vm.eventCatalog = entity;

        var unsubscribe = $rootScope.$on('brmApp:eventCatalogUpdate', function(event, result) {
            vm.eventCatalog = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
