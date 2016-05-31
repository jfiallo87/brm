'use strict';

describe('Controller Tests', function() {

    describe('FacilityEventCatalog Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFacilityEventCatalog, MockFacilityCatalog, MockEventCatalog, MockFacilityEventSeatingCatalog, MockFacilityEventServiceCatalog, MockFacilityEventSchedule;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFacilityEventCatalog = jasmine.createSpy('MockFacilityEventCatalog');
            MockFacilityCatalog = jasmine.createSpy('MockFacilityCatalog');
            MockEventCatalog = jasmine.createSpy('MockEventCatalog');
            MockFacilityEventSeatingCatalog = jasmine.createSpy('MockFacilityEventSeatingCatalog');
            MockFacilityEventServiceCatalog = jasmine.createSpy('MockFacilityEventServiceCatalog');
            MockFacilityEventSchedule = jasmine.createSpy('MockFacilityEventSchedule');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'FacilityEventCatalog': MockFacilityEventCatalog,
                'FacilityCatalog': MockFacilityCatalog,
                'EventCatalog': MockEventCatalog,
                'FacilityEventSeatingCatalog': MockFacilityEventSeatingCatalog,
                'FacilityEventServiceCatalog': MockFacilityEventServiceCatalog,
                'FacilityEventSchedule': MockFacilityEventSchedule
            };
            createController = function() {
                $injector.get('$controller')("FacilityEventCatalogDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'brmApp:facilityEventCatalogUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
