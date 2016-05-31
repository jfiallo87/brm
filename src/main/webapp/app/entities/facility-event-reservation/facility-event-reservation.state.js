(function() {
    'use strict';

    angular
        .module('brmApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('facility-event-reservation', {
            parent: 'entity',
            url: '/facility-event-reservation',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'FacilityEventReservations'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/facility-event-reservation/facility-event-reservations.html',
                    controller: 'FacilityEventReservationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('facility-event-reservation-detail', {
            parent: 'entity',
            url: '/facility-event-reservation/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'FacilityEventReservation'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/facility-event-reservation/facility-event-reservation-detail.html',
                    controller: 'FacilityEventReservationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'FacilityEventReservation', function($stateParams, FacilityEventReservation) {
                    return FacilityEventReservation.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('facility-event-reservation.new', {
            parent: 'facility-event-reservation',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/facility-event-reservation/facility-event-reservation-dialog.html',
                    controller: 'FacilityEventReservationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                start: null,
                                end: null,
                                guests: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('facility-event-reservation', null, { reload: true });
                }, function() {
                    $state.go('facility-event-reservation');
                });
            }]
        })
        .state('facility-event-reservation.edit', {
            parent: 'facility-event-reservation',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/facility-event-reservation/facility-event-reservation-dialog.html',
                    controller: 'FacilityEventReservationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FacilityEventReservation', function(FacilityEventReservation) {
                            return FacilityEventReservation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('facility-event-reservation', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('facility-event-reservation.delete', {
            parent: 'facility-event-reservation',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/facility-event-reservation/facility-event-reservation-delete-dialog.html',
                    controller: 'FacilityEventReservationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FacilityEventReservation', function(FacilityEventReservation) {
                            return FacilityEventReservation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('facility-event-reservation', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
