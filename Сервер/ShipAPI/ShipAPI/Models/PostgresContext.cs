using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;

namespace ShipAPI.Models;

public partial class PostgresContext : DbContext
{
    public PostgresContext()
    {
    }

    public PostgresContext(DbContextOptions<PostgresContext> options)
        : base(options)
    {
    }

    public virtual DbSet<Cabin> Cabins { get; set; }

    public virtual DbSet<Location> Locations { get; set; }

    public virtual DbSet<Schedule> Schedules { get; set; }

    public virtual DbSet<Status> Statuses { get; set; }

    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
#warning To protect potentially sensitive information in your connection string, you should move it out of source code. You can avoid scaffolding the connection string by using the Name= syntax to read it from configuration - see https://go.microsoft.com/fwlink/?linkid=2131148. For more guidance on storing connection strings, see https://go.microsoft.com/fwlink/?LinkId=723263.
        => optionsBuilder.UseNpgsql("Host=localhost;Port=5432;Database=postgres;Username=postgres;Password=123");

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Cabin>(entity =>
        {
            entity.HasKey(e => e.IdCabin).HasName("cabin_pkey");

            entity.ToTable("cabin", "ship");

            entity.HasIndex(e => e.Number, "cabin_number_key").IsUnique();

            entity.Property(e => e.IdCabin).HasColumnName("id_cabin");
            entity.Property(e => e.IdStatus).HasColumnName("id_status");
            entity.Property(e => e.IsDay)
                .HasDefaultValue(true)
                .HasColumnName("is_day");
            entity.Property(e => e.Number).HasColumnName("number");

            entity.HasOne(d => d.IdStatusNavigation).WithMany(p => p.Cabins)
                .HasForeignKey(d => d.IdStatus)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("cabin_id_status_fkey");
        });

        modelBuilder.Entity<Location>(entity =>
        {
            entity.HasKey(e => e.IdLocation).HasName("location_pk");

            entity.ToTable("location", "ship");

            entity.Property(e => e.IdLocation).HasColumnName("id_location");
            entity.Property(e => e.Position)
                .HasColumnType("character varying")
                .HasColumnName("position");
            entity.Property(e => e.Title)
                .HasColumnType("character varying")
                .HasColumnName("title");
        });

        modelBuilder.Entity<Schedule>(entity =>
        {
            entity.HasKey(e => e.IdSchedule).HasName("schedule_pk");

            entity.ToTable("schedule", "ship");

            entity.Property(e => e.IdSchedule).HasColumnName("id_schedule");
            entity.Property(e => e.Date).HasColumnName("date");
            entity.Property(e => e.EndTime).HasColumnName("end_time");
            entity.Property(e => e.IdLocation).HasColumnName("id_location");
            entity.Property(e => e.StartTime).HasColumnName("start_time");
            entity.Property(e => e.Title)
                .HasColumnType("character varying")
                .HasColumnName("title");

            entity.HasOne(d => d.IdLocationNavigation).WithMany(p => p.Schedules)
                .HasForeignKey(d => d.IdLocation)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("schedule_location_fk");
        });

        modelBuilder.Entity<Status>(entity =>
        {
            entity.HasKey(e => e.IdStatus).HasName("status_pkey");

            entity.ToTable("status", "ship");

            entity.Property(e => e.IdStatus).HasColumnName("id_status");
            entity.Property(e => e.Description).HasColumnName("description");
            entity.Property(e => e.Name)
                .HasMaxLength(255)
                .HasColumnName("name");
        });

        OnModelCreatingPartial(modelBuilder);
    }

    partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
}
