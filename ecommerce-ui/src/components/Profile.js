import React, { useEffect, useState } from "react";
import { callProfile } from "../api/userService";

function Profile() {
  const [profile, setProfile] = useState("");

  useEffect(() => {
    callProfile()
      .then(data => setProfile(data))
      .catch(err => console.error(err));
  }, []);

  return (
    <div>
      <h2>User Profile</h2>
      <p>{profile}</p>
    </div>
  );
}

export default Profile;