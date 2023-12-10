using RouteService.ProxyServ;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Security.Cryptography.X509Certificates;
using System.Text;
using System.Threading.Tasks;


namespace RouteService
{
    [DataContract]
    public class RouteSegment
    {
        [DataMember]
        public List<Step> steps { get; set; }
        [DataMember]
        public List<Position> routePositions {get; set; }
        [DataMember]
        public double distance { get; set; }
        [DataMember]
        public double duration { get; set; }
    }
    public class FeatureCollection
    {
        public string Type { get; set; }
        public Metadata Metadata { get; set; }
        public List<double> Bbox { get; set; }
        public List<Feature> Features { get; set; }
    }

    public class Metadata
    {
        public string Attribution { get; set; }
        public string Service { get; set; }
        public long Timestamp { get; set; }
        public Query Query { get; set; }
        public Engine Engine { get; set; }
    }

    public class Query
    {
        public List<List<double>> Coordinates { get; set; }
        public string Profile { get; set; }
        public string Format { get; set; }
    }

    public class Engine
    {
        public string Version { get; set; }
        public DateTime BuildDate { get; set; }
        public DateTime GraphDate { get; set; }
    }

    public class Feature
    {
        public List<double> Bbox { get; set; }
        public string Type { get; set; }
        public Properties Properties { get; set; }
        public Geometry Geometry { get; set; }
    }

    public class Properties
    {
        public int Transfers { get; set; }
        public int Fare { get; set; }
        public List<Segment> Segments { get; set; }
        public List<int> WayPoints { get; set; }
        public Summary Summary { get; set; }
    }

    public class Segment
    {
        public double Distance { get; set; }
        public double Duration { get; set; }
        public List<Step> Steps { get; set; }
    }
    [DataContract]
    public class Step
    {
        [DataMember]
        public double Distance { get; set; }
        [DataMember]
        public double Duration { get; set; }
        [DataMember]
        public int Type { get; set; }
        [DataMember]
        public string Instruction { get; set; }
        [DataMember]
        public string Name { get; set; }
        [DataMember]
        public List<int> WayPoints { get; set; }
    }

    public class Summary
    {
        public double Distance { get; set; }
        public double Duration { get; set; }
    }

    public class Geometry
    {
        public List<List<double>> Coordinates { get; set; }
        public string Type { get; set; }
    }
}
