import React, { useEffect, useState } from "react";
import { getProfile } from "../api/userService";

function Dashboard() {
  const [profile, setProfile] = useState("");

  useEffect(() => {
    getProfile().then(setProfile).catch(console.error);
  }, []);

  return (
    <div>
      <h2>User Dashboard</h2>
      <p>{profile}</p>
    </div>
  );
}

export default Dashboard;