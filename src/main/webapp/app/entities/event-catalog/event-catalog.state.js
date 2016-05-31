(function() {
    'use strict';

    angular
        .module('brmApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('event-catalog', {
            parent: 'entity',
            url: '/event-catalog',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'EventCatalogs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/event-catalog/event-catalogs.html',
                    controller: 'EventCatalogController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('event-catalog-detail', {
            parent: 'entity',
            url: '/event-catalog/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'EventCatalog'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/event-catalog/event-catalog-detail.html',
                    controller: 'EventCatalogDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'EventCatalog', function($stateParams, EventCatalog) {
                    return EventCatalog.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('event-catalog.new', {
            parent: 'event-catalog',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/event-catalog/event-catalog-dialog.html',
                    controller: 'EventCatalogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('event-catalog', null, { reload: true });
                }, function() {
                    $state.go('event-catalog');
                });
            }]
        })
        .state('event-catalog.edit', {
            parent: 'event-catalog',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/event-catalog/event-catalog-dialog.html',
                    controller: 'EventCatalogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EventCatalog', function(EventCatalog) {
                            return EventCatalog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('event-catalog', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('event-catalog.delete', {
            parent: 'event-catalog',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/event-catalog/event-catalog-delete-dialog.html',
                    controller: 'EventCatalogDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EventCatalog', function(EventCatalog) {
                            return EventCatalog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('event-catalog', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
