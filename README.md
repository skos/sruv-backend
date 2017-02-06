# SRU 5
## System Rejestracji Użytkowników V
Authors: SKOS PG - admin@ds.pg.gda.pl - http://skos.ds.pg.gda.pl 

# Establishing environment by hand
## Running it
```console
$ docker login gitlab.ds.pg.gda.pl:4567
[...]

$ docker pull gitlab.ds.pg.gda.pl:4567/sruv/docker-sruv-core
Using default tag: latest
latest: Pulling from sruv/docker-sruv-core
3690ec4760f9: Pull complete 
3a71289dd3d7: Pull complete 
a59652fc2fa5: Pull complete 
Digest: sha256:fbccf3dd9539e402c03637801de68f6cccbc22d4ab95ac33d758e5df6965b31b
Status: Downloaded newer image for gitlab.ds.pg.gda.pl:4567/sruv/docker-sruv-core:latest 
$ docker pull gitlab.ds.pg.gda.pl:4567/sruv/docker-sruv-postgres
Using default tag: latest
Trying to pull repository gitlab.ds.pg.gda.pl:4567/sruv/docker-sruv-postgres ... 
latest: Pulling from gitlab.ds.pg.gda.pl:4567/sruv/docker-sruv-postgres
3690ec4760f9: Already exists 
a3ed95caeb02: Pull complete 
d57e925e1ac0: Pull complete 
Digest: sha256:8cb012e7cc78c7b9c44a3e274917d730ed204dcb40c51cef0e9a5f399376949f
Status: Downloaded newer image for gitlab.ds.pg.gda.pl:4567/sruv/docker-sruv-postgres:latest

$ mkdir -p /var/lib/postgresql/data
$ chown -R 70:70 /var/lib/postgresql

$ docker run -ti -v /var/lib/postgresql/data:/var/lib/postgresql/data:Z -u postgres gitlab.ds.pg.gda.pl:4567/sruv/docker-sruv-postgres initdb
The files belonging to this database system will be owned by user "postgres".
This user must also own the server process.

The database cluster will be initialized with locales
  COLLATE:  C
  CTYPE:    C.UTF-8
  MESSAGES: C
  MONETARY: C
  NUMERIC:  C
  TIME:     C
The default database encoding has accordingly been set to "UTF8".
The default text search configuration will be set to "english".

Data page checksums are disabled.

fixing permissions on existing directory /var/lib/postgresql/data ... ok
creating subdirectories ... ok
selecting default max_connections ... 100
selecting default shared_buffers ... 128MB
selecting dynamic shared memory implementation ... posix
creating configuration files ... ok
creating template1 database in /var/lib/postgresql/data/base/1 ... ok
initializing pg_authid ... ok
initializing dependencies ... ok
creating system views ... ok
loading system objects' descriptions ... ok
creating collations ... sh: locale: not found
ok
No usable system locales were found.
Use the option "--debug" to see details.
creating conversions ... ok
creating dictionaries ... ok
setting privileges on built-in objects ... ok
creating information schema ... ok
loading PL/pgSQL server-side language ... ok
vacuuming database template1 ... ok
copying template1 to template0 ... ok
copying template1 to postgres ... ok
syncing data to disk ... ok

WARNING: enabling "trust" authentication for local connections
You can change this by editing pg_hba.conf or using the option -A, or
--auth-local and --auth-host, the next time you run initdb.

Success.

$ docker run -ti -v /var/lib/postgresql/data:/var/lib/postgresql/data:Z -u postgres gitlab.ds.pg.gda.pl:4567/sruv/docker-sruv-postgres /bin/sh
/ $ postgres &
/ $ LOG:  database system was shut down at 2017-02-06 22:00:37 UTC
LOG:  MultiXact member wraparound protections are now enabled
LOG:  database system is ready to accept connections
LOG:  autovacuum launcher started
/ $
/ $
/ $ psql
psql (9.5.4)
Type "help" for help.

postgres=# CREATE USER dbsruvuser WITH PASSWORD 'dbsruvpass';
CREATE ROLE
postgres=# CREATE DATABASE dbsruvbase;
CREATE DATABASE
postgres=# GRANT ALL PRIVILEGES ON DATABASE dbsruvbase TO dbsruvuser;
GRANT
^D
^D

$ echo "listen_addresses = '172.17.0.2'" >> /var/lib/postgresql/data/postgresql.conf
$ echo "host    all             all             172.17.0.3/32           trust" >> /var/lib/postgresql/data/pg_hba.conf

$ docker run -td --name docker-sruv-postgres -v /var/lib/postgresql/data:/var/lib/postgresql/data:Z -u postgres gitlab.ds.pg.gda.pl:4567/sruv/docker-sruv-postgres postgres
f59c41d511b6d8234ac4f0d6e8739d27f8d4e60a564cdf1b4c79cd5224f79944

$ docker run -td --link docker-sruv-postgres -p 8080:8080 -u sruser gitlab.ds.pg.gda.pl:4567/sruv/docker-sruv-core /usr/bin/java -jar /var/lib/sruv/sruv.jar
f1b653920286f922a11b88365b41c04d65c37f26395bbf2f364b10ab11332074
```

## Checking it
```console
$ docker ps
CONTAINER ID        IMAGE                                                COMMAND                  CREATED              STATUS              PORTS                    NAMES
f1b653920286        gitlab.ds.pg.gda.pl:4567/sruv/docker-sruv-core       "/usr/bin/java -jar /"   4 seconds ago        Up 2 seconds        0.0.0.0:8080->8080/tcp   distracted_elion
f59c41d511b6        gitlab.ds.pg.gda.pl:4567/sruv/docker-sruv-postgres   "postgres"               About a minute ago   Up About a minute   5432/tcp                 docker-sruv-postgres

$ ls -lah /var/lib/postgresql/data
razem 60K
drwx------. 19 70 70 4,0K 12-14 23:53 .
drwxr-xr-x.  3 70 70   17 12-14 23:51 ..
drwx------.  5 70 70   38 12-14 23:52 base
drwx------.  2 70 70 4,0K 12-14 23:53 global
drwx------.  2 70 70   17 12-14 23:52 pg_clog
drwx------.  2 70 70    6 12-14 23:52 pg_commit_ts
drwx------.  2 70 70    6 12-14 23:52 pg_dynshmem
-rw-------.  1 70 70 4,4K 12-14 23:52 pg_hba.conf
-rw-------.  1 70 70 1,6K 12-14 23:52 pg_ident.conf
drwx------.  4 70 70   37 12-14 23:52 pg_logical
drwx------.  4 70 70   34 12-14 23:52 pg_multixact
drwx------.  2 70 70   17 12-14 23:53 pg_notify
drwx------.  2 70 70    6 12-14 23:52 pg_replslot
drwx------.  2 70 70    6 12-14 23:52 pg_serial
drwx------.  2 70 70    6 12-14 23:52 pg_snapshots
drwx------.  2 70 70    6 12-14 23:53 pg_stat
drwx------.  2 70 70   24 12-14 23:54 pg_stat_tmp
drwx------.  2 70 70   17 12-14 23:52 pg_subtrans
drwx------.  2 70 70    6 12-14 23:52 pg_tblspc
drwx------.  2 70 70    6 12-14 23:52 pg_twophase
-rw-------.  1 70 70    4 12-14 23:52 PG_VERSION
drwx------.  3 70 70   58 12-14 23:52 pg_xlog
-rw-------.  1 70 70   88 12-14 23:52 postgresql.auto.conf
-rw-------.  1 70 70  21K 12-14 23:52 postgresql.conf
-rw-------.  1 70 70   18 12-14 23:53 postmaster.opts
-rw-------.  1 70 70   78 12-14 23:53 postmaster.pid

$ curl -u admin 127.0.0.1:8080/health
Enter host password for user 'admin':
{"status":"UP","diskSpace":{"status":"UP","total":10725883904,"free":10509852672,"threshold":10485760},"db":{"status":"UP","database":"PostgreSQL","hello":1}}
$ curl -u admin 127.0.0.1:8080/info
Enter host password for user 'admin':
{"app":{"name":"SRU-V"},"build":{"version":"2333a2fb55-20170113141152"}}

```

# Establishing environment with vagrant
Section bellow is not up to date. There were some modifications about creating default database, user, password and "listen_addresses" in postgresql.conf and allowing a client in pg_hba.conf that are not described here yet. 
Vagrant spins up the docker instances to bring up envrionment. There's no virtualization in between host and dockers (like vbox, kvm, etc.)
## Vagrantfile contnet
```console
VAGRANTFILE_API_VERSION = "2"
ENV['VAGRANT_NO_PARALLEL'] = 'yes'

Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|

  config.vm.define "initdb" do |initdb|
    initdb.vm.provider "docker" do |d|
      d.image = "gitlab.ds.pg.gda.pl:4567/sruv/docker-sruv-postgres"
      d.create_args = ["-u", "postgres"]
      d.volumes = ["/var/lib/postgresql/data:/var/lib/postgresql/data:Z"]
      d.cmd = ["initdb"]
      d.remains_running = false
      d.has_ssh = false
    end
  end

  config.vm.define "rundb" do |rundb|
    rundb.vm.provider "docker" do |d|
      d.image = "gitlab.ds.pg.gda.pl:4567/sruv/docker-sruv-postgres"
      d.create_args = ["-u", "postgres"]
      d.volumes = ["/var/lib/postgresql/data:/var/lib/postgresql/data:Z"]
      d.cmd = ["postgres"]
      d.has_ssh = false
    end
  end

  config.vm.define "sruv" do |sruv|
    sruv.vm.provider "docker" do |d|
      d.image = "gitlab.ds.pg.gda.pl:4567/sruv/docker-sruv-core"
      d.expose = ["8080"]
      d.create_args = ["-u", "sruser"]
      d.cmd = ["/usr/bin/java", "-jar", "/var/lib/sruv/sruv.jar"]
      d.has_ssh = false
    end
  end

end
```
## Usage
Here's the hard way - assuming SElinux. If you brave enough to... disable or not have one, you can skip "chcon" part.
1. Install docker and vagrant.
2. Run:

```console
mkdir -p /var/lib/postgresql/data
chown -R 70:70 /var/lib/postgresql
chcon -R -u system_u -t docker_var_lib_t /var/lib/postgresql
chcon -t svirt_sandbox_file_t /var/lib/postgresql/data
docker login gitlab.ds.pg.gda.pl:4567
vagrant up
```
# Establishing environment with terraform
# Establishing environment with other tools
