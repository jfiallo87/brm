(function() {
    'use strict';

    angular
        .module('brmApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('facility-event-seating-catalog', {
            parent: 'entity',
            url: '/facility-event-seating-catalog',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'FacilityEventSeatingCatalogs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/facility-event-seating-catalog/facility-event-seating-catalogs.html',
                    controller: 'FacilityEventSeatingCatalogController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('facility-event-seating-catalog-detail', {
            parent: 'entity',
            url: '/facility-event-seating-catalog/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'FacilityEventSeatingCatalog'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/facility-event-seating-catalog/facility-event-seating-catalog-detail.html',
                    controller: 'FacilityEventSeatingCatalogDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'FacilityEventSeatingCatalog', function($stateParams, FacilityEventSeatingCatalog) {
                    return FacilityEventSeatingCatalog.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('facility-event-seating-catalog.new', {
            parent: 'facility-event-seating-catalog',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/facility-event-seating-catalog/facility-event-seating-catalog-dialog.html',
                    controller: 'FacilityEventSeatingCatalogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                unitPrice: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('facility-event-seating-catalog', null, { reload: true });
                }, function() {
                    $state.go('facility-event-seating-catalog');
                });
            }]
        })
        .state('facility-event-seating-catalog.edit', {
            parent: 'facility-event-seating-catalog',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/facility-event-seating-catalog/facility-event-seating-catalog-dialog.html',
                    controller: 'FacilityEventSeatingCatalogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FacilityEventSeatingCatalog', function(FacilityEventSeatingCatalog) {
                            return FacilityEventSeatingCatalog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('facility-event-seating-catalog', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('facility-event-seating-catalog.delete', {
            parent: 'facility-event-seating-catalog',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/facility-event-seating-catalog/facility-event-seating-catalog-delete-dialog.html',
                    controller: 'FacilityEventSeatingCatalogDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FacilityEventSeatingCatalog', function(FacilityEventSeatingCatalog) {
                            return FacilityEventSeatingCatalog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('facility-event-seating-catalog', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
