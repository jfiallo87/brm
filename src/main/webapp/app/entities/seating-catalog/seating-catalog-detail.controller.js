(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('SeatingCatalogDetailController', SeatingCatalogDetailController);

    SeatingCatalogDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'SeatingCatalog', 'FacilityEventSeatingCatalog'];

    function SeatingCatalogDetailController($scope, $rootScope, $stateParams, entity, SeatingCatalog, FacilityEventSeatingCatalog) {
        var vm = this;

        vm.seatingCatalog = entity;

        var unsubscribe = $rootScope.$on('brmApp:seatingCatalogUpdate', function(event, result) {
            vm.seatingCatalog = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
