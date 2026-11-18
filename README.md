# City-Drop-Logistics

A command-line parcel delivery and logistics management application built in Java. Developed entirely using custom-implemented data structures and sorting algorithms without relying on Java's built-in collections framework, this system models delivery networks, manages customer records, schedules priority deliveries, and benchmarks sorting performance.

## Features

- **Delivery Network & Routing:** Models locations and weighted paths using a custom graph structure (`DSAGraphs`, `DSAGraphNode`, `DSAEdge`).
- **Customer Directory:** Stores and retrieves customer records parsed from `customers.csv` using a custom hash table (`DSAHashTable`, `DSAHashEntry`, `DSAHashCustomer`).
- **Priority Parcel Scheduling:** Manages and prioritizes `DeliveryRequest` items through `ParcelScheduler`, backed by a custom heap (`DSAHeap`, `DSAHeapEntry`).
- **Dataset Sorting & Testing:** Implements custom sorting routines (`DeliverySorts`, `DeliveryFileSorter`) alongside a benchmarking and validation harness (`DeliveryDatasetTester`).
- **Interactive CLI:** Provides a menu-driven console interface (`MainMenu`) supported by foundational custom structures (`DSALinkedList`, `DSAStacks`, `DSAQueues`).
