create table Farmers(
    Farmer_id int primary key,
    Farmer_name varchar(50) not null,
    Contact_no bigint not null,
    Address varchar(200) not null,
    no_of_cows int not null,
    Milk_production_capacity int not null,
    Account_no bigint not null,
    Date_of_joining date not null,
    years_together int not null
);

create table Cattle(
    Cattle_id int primary key,
    Farmer_id int not null,
    Vaccination_status varchar(10) not null,
    Age int not null,
    Offspring int not null,
    Identity_tag int not null,
    Medical_history varchar(200) not null,
    Milk_capacity int not null
);

create table Lab_report(
    Lab_report_id int primary key,
    District_id int not null,
    Pathogen_test int,
    Temperature_test int not null,
    fat_contents int,
    Protein_test int not null,
    Lactose_test int not null,
    DNF_test int not null,
    Milk_type varchar(50) not null
);

create table Branch(
    Branch_id int primary key,
    location varchar(50) not null,
    milk_supplied_by_district int not null,
    Lab_report_id int not null
);

create table District(
    District_id int primary key,
    District_name varchar(50) not null,
    Branch_id int not null
);

create table Milk_collector(
    Collector_id int primary key,
    Collector_name varchar(50) not null,
    Location varchar(50) not null,
    Milk_received int not null,
    Calorie_content int not null,
    Date_of_joining date not null,
    Account_no int not null,
    District_id int not null
);

create table Factory(
    Factory_id int primary key,
    Factory_name varchar(50) not null,
    Location varchar(50) not null,
    Milk_received int not null
);

create table Distributor(
    Distributor_id int primary key,
    Distributor_name varchar(50) not null,
    Factory_id int not null,
    Milk_supplied int not null
);

create table Subsidy(
    Subsidy_id int primary key,
    Farmer_id int not null,
    Subsidy_status varchar(200) not null,
    Subsidy_type varchar(50) not null,
    Privilege_amount int not null
);

alter table Cattle
add constraint fk_cattle_farmer foreign key (Farmer_id) references Farmers(Farmer_id);

alter table Distributor
add constraint fk_distributor_factory foreign key (Factory_id)  references Factory(Factory_id);

alter table Milk_collector
add constraint fk_collector_district foreign key (District_id)  references District(District_id);

alter table District
add constraint fk_district_branch foreign key (Branch_id)  references Branch(Branch_id);

alter table Branch
add constraint fk_branch_labreport foreign key (Lab_report_id)  references Lab_report(Lab_report_id);

alter table Lab_report
add constraint fk_labreport_district foreign key (District_id)  references District(District_id);

alter table Subsidy
add constraint fk_subsidy_farmer foreign key(Farmer_id)  references Farmers(Farmer_id);
