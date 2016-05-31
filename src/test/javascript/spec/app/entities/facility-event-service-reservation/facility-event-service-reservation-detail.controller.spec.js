'use strict';

describe('Controller Tests', function() {

    describe('FacilityEventServiceReservation Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFacilityEventServiceReservation, MockFacilityEventReservation, MockFacilityEventServiceCatalog;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFacilityEventServiceReservation = jasmine.createSpy('MockFacilityEventServiceReservation');
            MockFacilityEventReservation = jasmine.createSpy('MockFacilityEventReservation');
            MockFacilityEventServiceCatalog = jasmine.createSpy('MockFacilityEventServiceCatalog');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'FacilityEventServiceReservation': MockFacilityEventServiceReservation,
                'FacilityEventReservation': MockFacilityEventReservation,
                'FacilityEventServiceCatalog': MockFacilityEventServiceCatalog
            };
            createController = function() {
                $injector.get('$controller')("FacilityEventServiceReservationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'brmApp:facilityEventServiceReservationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
