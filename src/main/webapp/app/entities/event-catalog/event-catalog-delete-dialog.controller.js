(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('EventCatalogDeleteController',EventCatalogDeleteController);

    EventCatalogDeleteController.$inject = ['$uibModalInstance', 'entity', 'EventCatalog'];

    function EventCatalogDeleteController($uibModalInstance, entity, EventCatalog) {
        var vm = this;

        vm.eventCatalog = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EventCatalog.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
