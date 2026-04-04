# Multi-Level Parking Lot System

## Problem Statement

Design and implement a parking lot management system for a multi-level parking facility that efficiently handles vehicle entry, slot assignment, exit, and fee calculation.

## Overview

The system manages a parking lot with multiple levels, each containing categorized slots for different vehicle types. The goal is to assign vehicles to the most appropriate available slot as quickly as possible, calculate fees on exit, and provide administrators with real-time visibility and control over the facility.

## Vehicle Types & Slot Compatibility

| Vehicle | Motorcycle Slot | Car Slot | Bus Slot |
|---------|----------------|----------|----------|
| Motorcycle | ✅ | ✅ | ✅ |
| Car | ❌ | ✅ | ✅ |
| Bus | ❌ | ❌ | ✅ |

## Parking Fees

| Vehicle | Fee |
|---------|-----|
| Motorcycle | $1/hour |
| Car | $2/hour |
| Bus | $5/hour |

## Supported Commands
```
ADD_LEVEL <level_id> <num_motorcycle_slots> <num_car_slots> <num_bus_slots>
PARK_VEHICLE <vehicle_type> <license_plate>
EXIT_VEHICLE <license_plate>
VIEW_STATUS
ADD_SLOTS <level_id> <slot_type> <count>
REMOVE_SLOTS <level_id> <slot_type> <count>
```

## Sample Input/Output
```
ADD_LEVEL 1 10 20 5
→ Level 1 added with 10 motorcycle slots, 20 car slots, 5 bus slots.

PARK_VEHICLE CAR KA-01-HH-1234
→ Car with license plate KA-01-HH-1234 parked at level 1, slot 15.

VIEW_STATUS
→ Level 1: 5/10 motorcycle slots, 10/20 car slots, 2/5 bus slots available.

EXIT_VEHICLE KA-01-HH-1234
→ Car with license plate KA-01-HH-1234 exited. Fee: $10.
```

## Requirements

### Core
- Assign vehicles to the smallest compatible slot type to preserve larger slots for bigger vehicles
- Reject entry if no suitable slot is available
- Calculate fee accurately based on entry and exit timestamps
- Free slot immediately on vehicle exit

### Design
- Modular class design — `ParkingLot`, `ParkingLevel`, `ParkingSlot`, `Vehicle`, `Admin`
- Efficient data structures for O(1) or O(log n) slot lookup
- Extensible for new vehicle types or fee structures
- Thread-safe for concurrent vehicle entry/exit

### Edge Cases to Handle
- Parking lot full
- Vehicle exits without having entered
- Duplicate license plate entry
- Invalid commands or vehicle types
- Removing slots that are currently occupied

## Bonus
- Optimised slot assignment to minimise walking distance
- Advance reservation system
- Real-time status monitoring