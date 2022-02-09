create database Cinestar

use Cinestar

--tables
create table Director
(
	IDDirector int primary key identity,
	FirstName nvarchar(100) not null,
	Surname nvarchar(100) not null
)

create table Movie
(
	IDMovie int primary key identity,
	Title nvarchar(100) ,
	PublishedDate nvarchar(50) ,
	MovieDescription nvarchar(max) ,
	OriginalTitle nvarchar(100),
	DirectorID int ,
	Duration int ,
	Genre nvarchar(100),
	Poster nvarchar(100) ,--PicturePath
	Link nvarchar(100) ,
	StartDate nvarchar(100) ,

	foreign key (DirectorID) references Director(IDDirector)
)


create table Actor
(
	IDActor int primary key identity,
	FirstName nvarchar(100),
	Surname nvarchar(100) 
)


create table ActorsAndMovies
(
	ActorID int ,
	MovieID int ,

	foreign key (ActorID) references Actor(IDActor),
	foreign key (MovieID) references Movie(IDMovie)
)


create table Users
(
	IDUser int primary key identity,
	Username nvarchar(100) ,
	UserPassword nvarchar(max) ,
	IsAdmin bit 
)

--!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!DELETE ALL!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

--procedure za filmove
create proc createMovie
@IDMovie int output,
@Title nvarchar(100),
@PublishedDate nvarchar(50),
@MovieDescription nvarchar(max),
@OriginalTitle nvarchar(100),
@DirectorID int,
@Duration int,
@Genre nvarchar(100),
@Poster nvarchar(100),
@Link nvarchar(100),
@StartDate nvarchar(100)
as 	
insert into Movie values(@Title,@PublishedDate,@MovieDescription,@OriginalTitle,@DirectorID,@Duration,@Genre,@Poster,@Link,@StartDate)
set @IDMovie = SCOPE_IDENTITY()


create proc selectMovie
@IDMovie int
as
select * from Movie
where @IDMovie = Movie.IDMovie


create proc selectMovies
as
select * from Movie


create proc updateMovie
@IDMovie int,
@Title nvarchar(100),
@PublishedDate nvarchar(50),
@MovieDescription nvarchar(max),
@OriginalTitle nvarchar(100),
@DirectorID int,
@Duration int,
@Genre nvarchar(100),
@Poster nvarchar(100),
@Link nvarchar(100),
@StartDate nvarchar(100)
as
update Movie set Title = @Title, PublishedDate = @PublishedDate, MovieDescription = @MovieDescription, OriginalTitle = @OriginalTitle, DirectorID = @DirectorID,
Duration = @Duration, Genre = @Genre, Poster = @Poster, Link = @Link, StartDate = @StartDate
where IDMovie = @IDMovie


create proc deleteMovie
@IDMovie int
as
delete from Movie 
where IDMovie = @IDMovie


--procedure za glumce
create proc createActor
@IDActor int output,
@FirstName nvarchar(100),
@Surname nvarchar(100)
as
insert into Actor values(@FirstName,@Surname)
set @IDActor = SCOPE_IDENTITY()


create proc selectActor
@IDActor int
as
select * from Actor
where @IDActor = IDActor


create proc selectActors
as
select * from Actor


create proc deleteActor
@IDActor int
as
delete from Actor
where @IDActor = IDActor


create proc updateActor
@IDActor int,
@FirstName nvarchar(100),
@Surname nvarchar(100)
as
update Actor set FirstName = @FirstName, Surname = @Surname
where IDActor = @IDActor



--procedure za direktore
create proc createDirector
@IDDirector int output,
@FirstName nvarchar(100),
@Surname nvarchar(100)
as
insert into Director values(@FirstName,@Surname)
set @IDDirector = SCOPE_IDENTITY()


create proc selectDirector
@IDDirector int
as
select * from Director
where IDDirector = @IDDirector

create proc selectDirectors
as
select * from Director


create proc deleteDirector
@IDDirector int
as 
delete from Director 
where @IDDirector = IDDirector


create proc updateDirector
@IDDirector int,
@FirstName nvarchar(100),
@Surname nvarchar(100)
as
update Director set FirstName = @FirstName, Surname = @Surname
where @IDDirector = IDDirector


--procedure za usere
create proc createUser
@Username nvarchar(100),
@UserPassword nvarchar(100),
@IsAdmin bit,
@IDUser int output
as
insert into Users values(@Username,@UserPassword,@IsAdmin)
set @IDUser = SCOPE_IDENTITY()



create proc selectUser
@IDUser int
as
select * from Users
where @IDUser = IDUser


create proc selectUsers
as
select * from Users


create proc deleteUser
@IDUser int
as
delete from Users
where @IDUser = IDUser


create proc updateUser
@IDUser int,
@Username nvarchar(100),
@UserPassword nvarchar(100),
@IsAdmin bit
as
update Users set @Username = Username, @UserPassword = UserPassword,@IsAdmin = IsAdmin
where @IDUser = IDUser


create proc getUser
@Exists bit output,
@Username nvarchar(100),
@UserPassword nvarchar(100)
as
if exists(select * from Users where Username = @Username and UserPassword = @UserPassword)
set @Exists = 1
else 
set @Exists = 0


create proc findUser
@Username nvarchar(100),
@UserPassword nvarchar(100)
as
select * from Users 
where Username = @Username and UserPassword = @UserPassword


create proc checkUsername
@Exists bit output,
@Username nvarchar(100)
as
if exists (select * from Users where Username = @Username)
set @Exists = 1
else
set @Exists = 0


--procedure za ActorsAndMovies
create proc selectMoviesWithActor
@ActorID int
as
select m.* from Movie as m
inner join ActorsAndMovies as am 
on am.MovieID = m.IDMovie
where am.ActorID = @ActorID


create proc selectActorsInMovie
@MovieID int
as
select a.* from Actor as a
inner join ActorsAndMovies as am
on am.ActorID = a.IDActor
where am.MovieID = @MovieID

----------------------------------------
create proc InsertActorAndMovie
@ActorID int,
@MovieID int
as
insert into ActorsAndMovies values (@ActorID,@MovieID)


create proc RemoveMovie
@MovieID int
as
delete from ActorsAndMovies 
where @MovieID = MovieID


create proc RemoveActor
@ActorID int
as
delete from ActorsAndMovies 
where @ActorID = ActorID


--procedure za delete
create proc DeleteAll
as
delete from ActorsAndMovies
delete from Actor
delete from Movie
delete from Director